import React from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import Voting from './components/Voting';
import Email from './components/Email';


function App() {
  return (
    <Router>
      <div className="App">
      </div>
      <Switch>
         <Route path="/voting/:id" component={Voting}/> 
         <Route path="/mail" component={Email}/> 
      </Switch>
    </Router>
  );
}

export default App;
