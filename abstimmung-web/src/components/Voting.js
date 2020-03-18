import React, {useEffect, Fragment} from 'react';
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

    useEffect(() => {
      // https://www.robinwieruch.de/react-hooks-fetch-data
      console.log("MOuntie " +id);
      const fetchData = async () => {
        const [votingId, userId] = id.split(':');
        const response = await getVoting(votingId, userId);
        if (response) {
          let obj = response.data;
          Object.assign(obj, {votingId, userId});
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
            <Table data={voting}></Table>
          </Fragment>
        :
          ''
    )
}



export default Voting;