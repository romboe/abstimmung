import React, {useState, useEffect} from 'react';
import Option from './Option';

function OptionSelection(props) {
    const [state, setState] = useState({values:props.values})

    useEffect(() => {
        if (props.values) {
            setState({values:props.values});
        }
        
    }, [props]);

    const handleClick = (index) => {
        const values = state.values;
        values.splice(index, 1);
        setState({values});
        props.changeHandler(values);
    }

    return(
        <div className='list-inline'>
            {state.values.map((x, i) => <Option clickHandler={handleClick} key={i} index={i} value={x}></Option>)}
        </div>
    )
}

export default OptionSelection;