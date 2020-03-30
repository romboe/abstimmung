import React, {useEffect, useState} from 'react';
import {dm} from '../utils/dateUtils'

function Option(props) {

    const [state, setState] = useState('');

    useEffect(() => {
        if (typeof props.value.getMonth === 'function') {
            setState(dm(props.value));
        }
    }, [props]);

    const handleClick = () => {
        props.clickHandler(props.index);
    }
    return(
        <div className='list-inline-item border border-primary rounded'>
            <h5>{state}</h5>
            <button type="button" className="btn btn-outline-primary" onClick={handleClick}>-</button>
        </div>
    )
}

export default Option;