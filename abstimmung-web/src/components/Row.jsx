import React from 'react';
import CustomCheckbox from './custom/CustomCheckbox'
import {vote} from  "../api/api";
import {useSelector} from 'react-redux';

function Row(props) {

    const voting = useSelector(state => state.voting);

    const handleClick = (checkboxIndex, val) => {
        console.log(checkboxIndex + " is " + val);
        vote(voting.votingId, voting.userId, checkboxIndex, val);
    }

    return(
        props.data.map((d, index) => {
            console.log('row'  + d)
            return index === 0 ? <th scope="row">{d}</th> : <td><CustomCheckbox clickHandler={handleClick} index={index-1} enabled={props.enabled} value={d}/></td>
        })
    )
}

export default Row;