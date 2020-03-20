import axios from './config.axios';

export async function getVoting(votingId, voterId) {
    const url = votingId + ':' + voterId;
    let response;
    try {
        response = await axios.get(url);
        console.log('Antwort: ' + response);
    }
    catch(error) {
        console.log(error);
    }
    return response;
}

export async function vote(votingId, voterId, optionIndex, value) {
    try {
        await axios({
            method: 'put',
            url: '/votes',
            data: {
                votingId, // entspricht votingId: votingId
                voterId,
                optionIndex,
                value
            }
        });
    }
    catch(error) {
        console.log(error);
    }
}

export async function changeName(votingId, voterId, name) {
    try {
        await axios({
            method: 'post',
            url: '/users',
            data: {
                votingId, // entspricht votingId: votingId
                voterId,
                name
            }
        });
    }
    catch(error) {
        console.log(error);
    }
}