import React, {useState} from 'react';

function CustomCheckbox(props) {

    // useState ist ein Hook: https://reactjs.org/docs/hooks-state.html
    const [checked, setChecked] = useState(props.value);

    function tick() {
        console.log('tick')
        //
        setChecked(!checked)
    }

    return <input disabled={!props.enabled} type="checkbox" checked={checked} onClick={() => tick()}></input>
}

export default CustomCheckbox;