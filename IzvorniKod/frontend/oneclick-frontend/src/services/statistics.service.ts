import api from './api.service'
import getHostName from "./host.service";

const getRankings = () => {
    return api.get(getHostName("/statistics/rankings"));
}

const getMyStats = () => {
    return api.get(getHostName("/statistics/personal"));
}

const getGlobalStats = () => {
    return api.get(getHostName("/statistics/global"));
}

const StatisticsService = {getRankings, getMyStats, getGlobalStats}
export default StatisticsService;