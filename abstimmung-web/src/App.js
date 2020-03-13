import React from 'react';
import './App.css';
import Table from './components/Table';
import {voting} from './testdata/data.js';

function App() {
  return (
    <div className="App">
      <Table data={voting}></Table>
    </div>
  );
}

export default App;
