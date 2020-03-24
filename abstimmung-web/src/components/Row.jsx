import React from 'react';
import CustomCheckbox from './custom/CustomCheckbox'
import CustomInput from './custom/CustomInput'
import {vote, changeName} from  "../api/api";
import {useSelector} from 'react-redux';

function Row(props) {

    const voting = useSelector(state => state.voting);

    const handleClick = (checkboxIndex, val) => {
        console.log(checkboxIndex + " is " + val);
        vote(voting.votingId, voting.voterId, checkboxIndex, val);
    }

    const handleNameChange = (name) => {
        changeName(voting.votingId, voting.voterId, name);
    }

    return(
        props.data.map((d, index) => {
            return index === 0 ? <th scope="row"><CustomInput changeHandler={handleNameChange} value={d}></CustomInput></th> :
            <td><CustomCheckbox clickHandler={handleClick} index={index-1} enabled={props.enabled} value={d}/></td>
        })
    )
}

export default Row;