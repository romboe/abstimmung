import React, {useEffect, Fragment} from 'react';
import AddUser from './AddUser';
import Table from './Table';
//import {voting} from '../testdata/data.js';
import {useParams} from "react-router-dom";
import {getVoting} from  "../api/api.js";
// import {useState} from 'react';
import {useSelector, useDispatch} from 'react-redux';
import {initVoting} from '../actions';

function Voting() {
    const { id } = useParams();
    // const [voting, setVoting] = useState(null);
    const voting = useSelector(state => state.voting);
    const dispatch = useDispatch();

    const setupCounter = (rows) => {
      let counter = [];
        for (let i=1; i<rows.length; i++) {
            for (let columnIndex=1; columnIndex<rows[i].length; columnIndex++) {
                counter[columnIndex-1] = 0;
            }
        }
        for (let i=1; i<rows.length; i++) {
            for (let columnIndex=1; columnIndex<rows[i].length; columnIndex++) {
                // console.log(rows[i] + ':' + columnIndex + ' = ' + rows[i][columnIndex]);
                if (rows[i][columnIndex] === 'true') {
                  counter[columnIndex-1]++;
                }
            }
        }
        console.log(counter);
        return counter;
    }

    useEffect(() => {
      // https://www.robinwieruch.de/react-hooks-fetch-data
      console.log("MOuntie " +id);
      const fetchData = async () => {
        const [votingId, voterId] = id.split(':');
        const response = await getVoting(votingId, voterId);
        if (response) {
          let obj = response.data;
          const counter = setupCounter(obj.rows);
          Object.assign(obj, {votingId, voterId, counter});
          console.log(obj);

          dispatch(initVoting(obj));
          //setVoting(obj);
        }
      }
      fetchData();
    },[id,dispatch]);

    return(
        voting != null ?
          <Fragment>
            <h1>{voting.name}</h1>
            <p>{voting.description}</p>
            <Table data={voting}></Table>
            {voting.admin || (voting.enabledRow < 0 && voting.voterId) ? <AddUser></AddUser> : ''}
          </Fragment>
        :
          ''
    )
}



export default Voting;