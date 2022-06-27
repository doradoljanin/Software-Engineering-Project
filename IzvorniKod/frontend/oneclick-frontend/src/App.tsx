import React from 'react';
import './App.css';
import Header from './components/basic/header';
import HomeRouter from './routers/home.router';
import {MuiPickersUtilsProvider} from '@material-ui/pickers';
import DateFnsUtils from '@date-io/date-fns';

const media =
{
  desktop: '@media(min-width: 1000px)'
}

class App extends React.Component {
  render() {
    return (
      <div>
        <MuiPickersUtilsProvider utils={DateFnsUtils}>
          <Header />
          <HomeRouter />
        </MuiPickersUtilsProvider>
      </div>
    );
  }
}

export default App;


