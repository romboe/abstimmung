import React, {useState} from 'react';
import CustomEmail from './custom/CustomEmail';
import CustomInput from './custom/CustomInput';
import {useSelector} from 'react-redux';
import {addUser} from  "../api/api";
import { useHistory } from "react-router-dom";

function AddUser() {

    const [state, setState] = useState({name:'', email:''});
    const voting = useSelector(state => state.voting);
    const history = useHistory();

    const handleClick = async () => {
        let response = await addUser(voting.votingId, state.name, state.email);
        if (response) {
            history.push("/votings/" + voting.votingId + ":" + response.data.id);
        }
    }

    const handleNameChange = (val) => {
        setState(Object.assign(state, {name:val}));
    }

    const handleEmailChange = (index, val) => {
        setState(Object.assign(state, {email:val}));
    }

    return (
        <div>
            <div>
                Name <CustomInput changeHandler={handleNameChange} value={state.name}/>
            </div>
            <div>
                <CustomEmail changeHandler={handleEmailChange} value={state.email}/>
            </div>
            <div>
                <button onClick={handleClick}>+</button>
            </div>
        </div>
    );
}

export default AddUser;