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

app.get('/', function(req, res){
    res.render('index');
});

app.get('/dashboard', function(req, res){
    res.render('dashboard');
});

app.get('/patient', function(req, res){

  console.log(database);
  /*GET TEXT FROM CLOUD*/
  const text = 'I love this world';
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
        score: sentiment.score
    };

    res.render('patient', patientInfo);
  })
  .catch(err => {
    console.error('ERROR:', err);
    res.render('error');
  });	  
});

server.listen(8000, function(){

});

io.on('connection', function(socket) {
   socket.on('henlo', function () {
      console.log('A user disconnected');
   });
});