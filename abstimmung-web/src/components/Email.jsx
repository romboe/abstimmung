import React, {useState} from 'react';
import CustomEmail from './custom/CustomEmail';

function Email() {
    const [state, setState] = useState({recipients:[]});

    const addRecipient = () => {
        let modifiedState = Object.assign({}, state);
        modifiedState.recipients.push('ok ');
        setState(modifiedState);
        inputs.push(<div>hallo</div>);
    }

    const handleChange = () => {

    }

    let inputs = [];

    return(
        <div>
            {state.recipients.map(x => <CustomEmail changeHandler={handleChange} value={x}></CustomEmail>)}
            <button onClick={addRecipient}>Add</button>
        </div>
    );
}

export default Email;