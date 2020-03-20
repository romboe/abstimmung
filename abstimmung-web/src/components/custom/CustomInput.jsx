import React, {useState} from 'react';

function CustomInput(props) {
    const [state, setState] = useState(props.value);
    const handleChange = (e) => {
        const val = e.target.value;
        setState(val);
    }
    const handleBlur = (e) => {
        props.changeHandler(state);
    }
    return(
        <input onChange={handleChange} onBlur={handleBlur} type="text" value={state}></input>
    );
}

export default CustomInput;