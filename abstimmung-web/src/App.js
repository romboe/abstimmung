import React from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import Voting from './components/Voting';


function App() {
  return (
    <Router>
      <div className="App">
      </div>
      <Switch>
         <Route path="/:id" component={Voting}/> 
      </Switch>
    </Router>
  );
}

export default App;
