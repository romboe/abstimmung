import React from 'react';
import Row from './Row';
import {useSelector} from 'react-redux';

function Table(props) {

    const voting = useSelector(state => state.voting);

    if (props.data.rows.length === 0) {
        return ''
    }
    let head =
    <thead>
        <tr>{props.data.rows[0].map((d,i) => {
            if (i > 0) {
                return <th key={i}>#{voting.counter[i-1]}</th>
            }
            return <th key={i}></th>
            })}
        </tr>
        <tr>{props.data.rows[0].map((d,i) => <th key={i}>{d}</th>)}</tr>
    </thead>
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