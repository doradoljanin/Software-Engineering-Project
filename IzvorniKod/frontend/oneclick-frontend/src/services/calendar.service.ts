import api from './api.service'
import getHostName from "./host.service";

export const getCalendar = () => {
    return api.get(getHostName("/calendar"));
}

const CalendarService = {getCalendar}
export default CalendarService;