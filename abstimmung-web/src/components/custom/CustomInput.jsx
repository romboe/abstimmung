import React, {useState} from 'react';

function CustomInput(props) {
    const [state, setState] = useState(props.value);
    const handleChange = (e) => {
        const val = e.target.value;
        setState(val);
    }
    const handleBlur = (e) => {
        if (props.changeHandler) {
            props.changeHandler(state);
        }
    }
    return(
        <input readOnly={props.readonly} onChange={handleChange} onBlur={handleBlur} type="text" value={state}></input>
    );
}

export default CustomInput;