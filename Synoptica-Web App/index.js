var express = require('express');

var app = express();
var server =  require('http').createServer(app);
var path = require('path');
var hbs = require('hbs');
var io = require('socket.io').listen(server);

// Imports the Google Cloud client library
const language = require('@google-cloud/language');
const admin = require('firebase-admin');
var defaultapp = admin.initializeApp();
const db = admin.firestore();

var patientID = "";

var plotly = require('plotly')("jyoungk", "dQmpdr8BqTlUD6yh5cGg")
var data = [
    {
      x: [],
      y: [],
      type: "scatter"
    }
  ];

// Instantiates a client
const client = new language.LanguageServiceClient();

app.set('port', process.env.PORT || 8000 );
app.set('view engine', 'hbs');

var database = [];

db.collection('patients').get().then(snapshot => {
  snapshot.docs.forEach(doc => {
      add(doc);
  });
});

function add(a) {
  database[a.id] = a.data();
}

function process_dates(id) {
  var a = "";
  var o = database[id].entries;
  o.sort(function(a,b){
    console.log();
    return new Date(a.date)-new Date(b.date);
  });
  data = [
    {
      x: [],
      y: [],
      type: "scatter"
    }
  ];
  for(var i = o.length-1; i > Math.max(-1, o.length-8); i--) {
    data[0].x.push(new Date(database[id].entries[i].date));
    data[0].y.push(database[id].entries[i].score);
  }
  var layout = {
  title: "Mood Scores Past 7 Entries",
    xaxis: {
      title: "Dates",
      titlefont: {
        family: "Courier New, monospace",
        size: 18,
        color: "#7f7f7f"
      }
    },
    yaxis: {
      title: "Score",
      titlefont: {
        family: "Courier New, monospace",
        size: 18,
        color: "#7f7f7f"
      }
    }
  };
  var graphOptions = {filename: "date-axes", fileopt: "overwrite", layout: layout};
  plotly.plot(data, graphOptions, function (err, msg) {
      console.log(msg);
  });

  console.log(data[0].x, data[0].y);
  for(var i = o.length-1; i > o.length-4; i--) {
    var color="";
    if(database[id].entries[i].score > 0) {
      color="green";
    }
    else {
      color="red";
    }
    a += "<div class='dateEntry col-sm-4'>\
      <h1 class='dateHeader'>"+database[id].entries[i].date.toString().substring(0,15)+ "</h1>\
      <video width='320' height='240' controls>\
        <source src="+database[id].entries[i].video_uri+" type='video/mp4'>\
      </video>\
       <h1>"+database[id].entries[i].tag+ "</h1>\
       <h1 class="+color+">"+database[id].entries[i].score+ "</h1>\
      </div>";
  }
    return a;
}

function getMembers() {
  var s = "";
  for(var key in database) {
    s+="<a href=''><p onclick='generateProfile("+key+")'>"+database[key].name+"</p></a>";
    console.log(key);
  }
  return s;
}



app.get('/', function(req, res){
    res.render('index');
});

app.get('/dashboard', function(req, res){
  dashboardInfo = {
    members: getMembers()
  };
  res.render('dashboard', dashboardInfo);

});

app.get('/patient', function(req, res){
  /*GET TEXT FROM CLOUD
  const text = 'I hate this world';
  const document = {
    content: text,
    type: 'PLAIN_TEXT',
  };

  // Detects the sentiment of the text
  client
  .analyzeSentiment({document: document})
  .then(results => {
    const sentiment = results[0].documentSentiment;
    patientInfo = { 
        pfp: "https://images-na.ssl-images-amazon.com/images/I/71TTbee5oKL._SX466_.jpg",
        name: "Joey",
        age: 14,
        symptoms: "Cancer",
        score: sentiment.score,
        squares: process_dates('hIzHox3fPvxrbRVcCJ6P')
    };

    res.render('patient', patientInfo);
  })
  .catch(err => {
    console.error('ERROR:', err);
    res.render('error');
  });	  
  */
  patientInfo = { 
        pfp: database['hIzHox3fPvxrbRVcCJ6P'].profile_pic,
        name: database['hIzHox3fPvxrbRVcCJ6P'].name ,
        age: database['hIzHox3fPvxrbRVcCJ6P'].age ,
        symptoms: database['hIzHox3fPvxrbRVcCJ6P'].illness,
        squares: process_dates('hIzHox3fPvxrbRVcCJ6P'),
        average: data[0].y.reduce((a, b) => a + b, 0) / data[0].y.length,
        medication: database['hIzHox3fPvxrbRVcCJ6P'].medications
    };

 res.render('patient', patientInfo);
});

server.listen(8000, function(){

});

io.on('connection', function(socket) {
   socket.on('generate', function (data) {
      patientID=data.data;
      
   });
});