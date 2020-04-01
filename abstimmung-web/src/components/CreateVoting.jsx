import React, {useState} from 'react';
import CustomInput from './custom/CustomInput'
import DateRangePicker from '@wojtekmaj/react-daterange-picker';
import OptionSelection from './OptionSelection';
import {createVoting} from  "../api/api";
import { dm } from '../utils/dateUtils';
import { useHistory } from 'react-router-dom';
// import {useSelector} from 'react-redux';

function CreateVoting(props) {

    const [state, setState] = useState({votingName: '', description: '', creatorName: '', creatorEmail: '', optionRange:[], optionValues:[]});
    const history = useHistory();
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
            const formattedDates = dates.map(x => dm(x));
            setState(s => { return {...s, optionRange:formattedDates}});
        }
    }

    const handleOptionChange = (values) => {
        setState(s => { return {...s, optionValues:values}});
    }

    const handleNameChange = (val) => {
        setState(Object.assign(state, {creatorName:val}));
    }

    const handleClick = async () => {
        console.log(JSON.stringify(state, null, 2));
        const response = await createVoting({
            votingName: state.votingName,
            description: state.description,
            creatorName: state.creatorName,
            creatorEmail: state.creatorEmail,
            options: state.optionValues
        });
        if (response) {
            console.log(response)
            const id = response.data;
            history.push('/votings/' + id);
        }
    }

    return(
        <div className="container">
            <div className="row">
                <div className="col-2 text-right">Name</div>
                <div className="col"><CustomInput changeHandler={val => setState(Object.assign(state, {votingName:val}))} value={state.votingName}></CustomInput></div>
            </div>
            <div className="row">
                <div className="col-2 text-right">Beschreibung</div>
                <div className="col"><CustomInput changeHandler={val => setState(Object.assign(state, {description:val}))} value={state.description}></CustomInput></div>
            </div>
            <div className="row">
                <div className="col-2 text-right">Ersteller</div>
                <div className="col"><CustomInput changeHandler={handleNameChange} value={state.creatorName}></CustomInput></div>
            </div>
            <div className="row">
                <div className="col-2 text-right">E-Mail</div>
                <div className="col"><CustomInput changeHandler={val => setState(Object.assign(state, {creatorEmail:val}))} value={state.creatorEmail}></CustomInput></div>
            </div>
            <div className="row">
                <div className="col-2 text-right">Range</div>
                <div className="col"><DateRangePicker onChange={handleDateChange} isOpen={false} format="dd.MMM yyyy" value={new Date()}></DateRangePicker></div>
            </div>
            <div className="row">
                <div className="col-2 text-right">Optionen</div>
                <div className="col"><OptionSelection changeHandler={handleOptionChange} values={state.optionRange}></OptionSelection></div>
            </div>
            <div className="row">
                <div className="col-2 text-right"></div>
                <div className="col"><button onClick={handleClick}>Abstimmung erstellen</button></div>
            </div>
        </div>
    )
}

export default CreateVoting;