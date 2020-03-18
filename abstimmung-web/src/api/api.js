import axios from './config.axios';

export async function getVoting(votingId, userId) {
    const url = votingId + ':' + userId;
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

export async function vote(votingId, userId, optionIndex, value) {
    try {
        await axios({
            method: 'put',
            url: '/votes',
            data: {
                votingId, // entspricht votingId: votingId
                userId,
                optionIndex,
                value
            }
        });
    }
    catch(error) {
        console.log(error);
    }
}