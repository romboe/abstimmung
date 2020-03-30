import React, {useState} from 'react';
import CustomInput from './custom/CustomInput'
import DateRangePicker from '@wojtekmaj/react-daterange-picker';
import OptionSelection from './OptionSelection';
import {createVoting} from  "../api/api";
// import {useSelector} from 'react-redux';

function CreateVoting(props) {

    const [state, setState] = useState({votingName: '', description: '', creatorName: '', creatorEmail: '', optionRange:[], optionValues:[]});
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

    const handleDateChange = (val) => {
        const [begin, end] = val;
        if (begin !== undefined && end !== undefined) {
            const dates = getDates(begin, end);
            setState(s => { return {...s, optionRange:dates}});
        }
    }

    const handleOptionChange = (values) => {
        setState(s => { return {...s, optionValues:values}});
    }

    const handleNameChange = (val) => {
        setState(Object.assign(state, {creatorName:val}));
    }

    const handleClick = () => {
        console.log(JSON.stringify(state, null, 2));
        createVoting({
            votingName: state.votingName,
            description: state.description,
            creatorName: state.creatorName,
            creatorEmail: state.creatorEmail,
            options: state.optionValues
        })
    }

    return(
        <div>
            <div>Name <CustomInput changeHandler={val => setState(Object.assign(state, {votingName:val}))} value={state.votingName}></CustomInput></div>
            <div>Descr <CustomInput changeHandler={val => setState(Object.assign(state, {description:val}))} value={state.description}></CustomInput></div>
            <div>Creator <CustomInput changeHandler={handleNameChange} value={state.creatorName}></CustomInput></div>
            <div>E-Mail <CustomInput changeHandler={val => setState(Object.assign(state, {creatorEmail:val}))} value={state.creatorEmail}></CustomInput></div>
            <div><DateRangePicker onChange={handleDateChange} isOpen={true} format="dd.MMM yyyy" value={new Date()}></DateRangePicker></div>
            <div><OptionSelection changeHandler={handleOptionChange} values={state.optionRange}></OptionSelection></div>
            <button onClick={handleClick}>Abstimmung erstellen</button>
        </div>
    )
}

export default CreateVoting;