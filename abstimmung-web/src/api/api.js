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
                userId: voterId,
                name
            }
        });
    }
    catch(error) {
        console.log(error);
    }
}

export async function addUser(votingId, name, email) {
    let response;
    try {
        response = await axios({
            method: 'put',
            url: '/users',
            data: {
                votingId, // entspricht votingId: votingId
                name,
                email
            }
        });
    }
    catch(error) {
        console.log(error);
    }
    return response;
}

export async function createVoting(input) {
    let response;
    try {
        response = await axios({
            method: 'put',
            url: '/',
            data: {
                votingName: input.votingName, 
                description: input.description,
                creatorName: input.creatorName,
                creatorEmail: input.creatorEmail,
                options: input.options
            }
        });
    }
    catch(error) {
        console.log(error);
    }
    return response;
}