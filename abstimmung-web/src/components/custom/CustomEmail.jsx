import React, {useState} from 'react';

function CustomEmail() {
    const [state, setState] = useState('');
    const change = (e) => {
        setState(e.target.value);
        // props.changeHandler();
    }
    return(
        <div>
            Email <input type="text" onChange={change} value={state}></input>
        </div>
    );
}

export default CustomEmail;