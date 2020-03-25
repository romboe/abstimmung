import React from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import Voting from './components/Voting';
import Email from './components/Email';
import CreateVoting from './components/CreateVoting';


function App() {
  return (
    <Router>
      <div className="App">
      </div>
      <Switch>
         <Route path="/votings/:id" component={Voting}/> 
         <Route path="/mail" component={Email}/> 
         <Route path="/create" component={CreateVoting}/> 
      </Switch>
    </Router>
  );
}

export default App;
