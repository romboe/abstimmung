export const initVoting = (voting) => {
    return {
        type: 'SET',
        payload: voting
    }
}

export const updateCounter = (counter) => {
    return {
        type: 'UPDATE_COUNTER',
        payload: {counter}
    }
}