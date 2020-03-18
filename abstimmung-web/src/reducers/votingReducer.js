// https://redux.js.org/recipes/structuring-reducers/refactoring-reducer-example/

function updateObject(oldObject, newValues) {
    // Encapsulate the idea of passing a new object as the first parameter
    // to Object.assign to ensure we correctly copy data instead of mutating
    return Object.assign({}, oldObject, newValues)
}

const votingReducer = (state, action) => {
    switch (action.type) {
        case 'SET':
            return updateObject(state, action.payload)
        default:
            return null
    }
};

export default votingReducer;