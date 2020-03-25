import React, {useState} from 'react';
import CustomInput from './custom/CustomInput'
import DateRangePicker from '@wojtekmaj/react-daterange-picker';
import OptionSelection from './OptionSelection';
// import {vote, changeName} from  "../api/api";
// import {useSelector} from 'react-redux';

function CreateVoting(props) {

    const [state, setState] = useState({optionValues:[]});
    /*
    const voting = useSelector(state => state.voting);

    const handleClick = (checkboxIndex, val) => {
        console.log(checkboxIndex + " is " + val);
        vote(voting.votingId, voting.voterId, checkboxIndex, val);
    }

    const handleNameChange = (name) => {
        changeName(voting.votingId, voting.voterId, name);
    }
*/

    const getDates = (startDate, stopDate) => {
        var ret = [];
        var currentDate = startDate;
        while (currentDate <= stopDate) {
            let date = new Date(currentDate);
            ret.push(date);
            currentDate.setDate(currentDate.getDate() + 1);
        }
        return ret;
    }

    const handleChange = (val) => {
        const [begin, end] = val;
        if (begin !== undefined && end !== undefined) {
            const optionValues = getDates(begin, end);
            setState({optionValues});
        }
        
        console.log(val);
    }

    const handleNameChange = () => {

    }

    return(
        <div>
            <CustomInput changeHandler={handleNameChange}></CustomInput>
            <DateRangePicker onChange={handleChange} isOpen={true} format="dd.MMM yyyy" value={new Date()}></DateRangePicker>
            <OptionSelection values={state.optionValues}></OptionSelection>
        </div>
    )
}

export default CreateVoting;