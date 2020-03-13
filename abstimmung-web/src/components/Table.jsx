import React from 'react';
import Row from './Row';

function Table(props) {
    if (props.data.rows.length === 0) {
        return ''
    }
    let head = <thead><tr>{props.data.rows[0].map(d => <th>{d}</th>)}</tr></thead>
    let body = [];
    for (let i=1; i<props.data.rows.length; i++) {
        console.log(props.data.rows[i]);
        body.push(<tr><Row enabled={i === props.data.enabledRow} data={props.data.rows[i]}/></tr>);
    }
    return(
        <table class="table table-striped">
            {head}
            <tbody>
            {body}
            </tbody>
        </table>
    )
}

export default Table;