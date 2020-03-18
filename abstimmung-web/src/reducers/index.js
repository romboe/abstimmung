import votingReducer from './votingReducer'
import {combineReducers} from 'redux';

export default combineReducers({
    voting: votingReducer
});