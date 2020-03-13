import React from 'react';
import CustomCheckbox from './custom/CustomCheckbox'

function Row(props) {
    return(
        props.data.map((d, index) => {
            console.log('row'  + d)
            return index === 0 ? <th scope="row">{d}</th> : <td><CustomCheckbox enabled={props.enabled} value={d}/></td>
        })
    )
}

export default Row;