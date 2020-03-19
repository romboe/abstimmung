import React, {useState} from 'react';
import CustomEmail from './custom/CustomEmail';

function Email() {
    const [state, setState] = useState({recipients:[]});

    const addRecipient = () => {
        let modifiedState = Object.assign({}, state);
        modifiedState.recipients.push('');
        setState(modifiedState);
    }

    const removeRecipient = (e) => {
        const index = e.target.name;
        let modifiedState = Object.assign({}, state);
        modifiedState.recipients.splice(index, 1);
        setState(modifiedState);
    }

    const handleChange = (i, val) => {
        let modifiedState = Object.assign({}, state);
        modifiedState.recipients[i] = val;
        setState(modifiedState);
    }

    const sendInvitation = () => {
        state.recipients.map(x => console.log(x));
    }

    return(
        <div>
            {state.recipients.map((x,i) =>
                <div className='table' style={{width:'300px'}}>
                    <div className='table-row'>
                        <div className='table-cell'>
                            <CustomEmail changeHandler={handleChange} index={i} value={x}></CustomEmail>
                        </div>
                        <div className='table-cell'>
                            <button name={i} onClick={removeRecipient}>-</button>
                        </div>
                    </div>
                </div>)}
            <button onClick={addRecipient}>Hinzufuegen</button>
            <button onClick={sendInvitation}>Einladung senden</button>
        </div>
    );
}

export default Email;