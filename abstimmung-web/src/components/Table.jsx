import React from 'react';
import Row from './Row';

function Table(props) {
    if (props.data.rows.length === 0) {
        return ''
    }
    let head = <thead><tr>{props.data.rows[0].map((d,i) => <th key={i}>{d}</th>)}</tr></thead>
    let body = [];
    for (let i=1; i<props.data.rows.length; i++) {
        console.log(props.data.rows[i]);
        body.push(<tr key={i}><Row enabled={i === props.data.enabledRow} data={props.data.rows[i]} key={i}/></tr>);
    }
    return(
        <table className="table table-striped">
            {head}
            <tbody>
            {body}
            </tbody>
        </table>
    )
}

export default Table;