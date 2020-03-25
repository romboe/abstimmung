import React, {useState, useEffect} from 'react';
import Option from './Option';

function OptionSelection(props) {
    const [state, setState] = useState({values:props.values})

    useEffect(() => {
        setState({values:props.values});
    }, [props]);

    const handleClick = (index) => {
        const values = state.values;
        values.splice(index, 1);
        setState({values});
    }

    return(
        <div class='list-inline'>
            {state.values.map((x, i) => <Option clickHandler={handleClick} index={i} value={x}></Option>)}
        </div>
    )
}

export default OptionSelection;