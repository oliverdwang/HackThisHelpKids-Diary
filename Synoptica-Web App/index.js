var express = require('express');
var app = express();
var path = require('path');
var hbs = require('hbs');


// Imports the Google Cloud client library
const language = require('@google-cloud/language');

// Instantiates a client
const client = new language.LanguageServiceClient();


app.set('port', process.env.PORT || 8000 );
app.set('view engine', 'hbs');


app.get('/', function(req, res){
    res.render('index');
});

app.get('/dashboard', function(req, res){
    res.render('dashboard');
});

app.get('/patient', function(req, res){
	

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
  });	  
});

var listener = app.listen(app.get('port'), function() {
  console.log( 'Express server started on port: '+listener.address().port );
});
