import React, {useState} from 'react';

function CustomCheckbox(props) {

    // useState ist ein Hook: https://reactjs.org/docs/hooks-state.html
    const [checked, setChecked] = useState(isTrue(props.value));

    function tick(e) {
        const val = !checked;
        setChecked(val);
        console.log('tick ' + val);
        props.clickHandler(props.index, val);
    }

    return <input disabled={!props.enabled} type="checkbox" checked={checked} onClick={tick}></input>
}

function isTrue(val) {
    return val === true || val === 'true';
}

export default CustomCheckbox;