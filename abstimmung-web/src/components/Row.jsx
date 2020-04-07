import React from 'react';
import CustomCheckbox from './custom/CustomCheckbox'
import CustomInput from './custom/CustomInput'
import {vote, changeName} from  "../api/api";
import {useSelector, useDispatch} from 'react-redux';
import {updateCounter} from "../actions";

function Row(props) {

    const voting = useSelector(state => state.voting);
    const dispatch = useDispatch();

    const handleClick = (checkboxIndex, val) => {
        console.log(checkboxIndex + " is " + val);
        let counter = voting.counter;
        if (val === true) {
            counter[checkboxIndex]++;
        }
        else {
            counter[checkboxIndex]--;
        }
        dispatch(updateCounter(counter));
        vote(voting.votingId, voting.voterId, checkboxIndex, val);
    }

    const handleNameChange = (name) => {
        changeName(voting.votingId, voting.voterId, name);
    }

    return(
        props.data.map((d, index) => {
            return index === 0 ? <th key={index} scope="row"><CustomInput changeHandler={handleNameChange} readonly={!props.enabled} value={d}></CustomInput></th> :
            <td key={index}><CustomCheckbox clickHandler={handleClick} index={index-1} enabled={props.enabled} value={d}/></td>
        })
    )
}

export default Row;