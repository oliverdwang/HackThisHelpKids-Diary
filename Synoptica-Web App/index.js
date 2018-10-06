var express = require('express');
var app = express();
var path = require('path');
var hbs = require('hbs');

app.set('port', process.env.PORT || 8000 );
app.set('view engine', 'hbs');


app.get('/', function(req, res){
    res.render('index');
});

app.get('/dashboard', function(req, res){
    res.render('dashboard');
});

var listener = app.listen(app.get('port'), function() {
  console.log( 'Express server started on port: '+listener.address().port );
});