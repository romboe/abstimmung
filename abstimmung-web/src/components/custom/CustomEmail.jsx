import React, {useState} from 'react';

function CustomEmail(props) {
    const [state, setState] = useState(props.value);

    const change = (e) => {
        const val = e.target.value;
        setState(val);
        props.changeHandler(props.index, val);
    }

    return(
        <div>
            Email <input type="text" onChange={change} value={state}></input>
        </div>
    );
}

export default CustomEmail;