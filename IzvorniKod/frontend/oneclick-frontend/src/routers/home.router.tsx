import React from 'react';
import { Router, Switch, Route } from 'react-router-dom';
import Index from '../components/screens/index.screen';
import history from '../misc/history';
import Login from '../components/screens/login.screen';
import Registration from '../components/screens/registration.screen';
import CitizenRegistration from '../components/screens/citizenRegistration.screen';
import AssociationRegistration from '../components/screens/associationRegistration.screen';
import AssociationScreen from '../components/screens/association.screen';
import Statistics from '../components/screens/statistics.screen';
import WalkConfirmationScreen from '../components/screens/walkConfirmation.screen';
import WalkConfirmedScreen from '../components/screens/walkConfirmed.screen';
import CalendarScreen from '../components/screens/calendar.screen';
import Profile from '../components/screens/profile.screen';
import AssociationEditScreen from '../components/screens/associationEdit.screen';
import CitizenAccountEdit from '../components/screens/citizenEdit.screen';
import SearchScreen from '../components/screens/search.screen';
import AnimalEditScreen from '../components/screens/animalEdit.screen';
import AnimalAddScreen from '../components/screens/animalAdd.screen';

function HomeRouter() {
    return (
        <Router history={history}>
            <div className="page">
                <Switch>
                    <Route exact path="/" component={Index}/>
                    <Route path="/login" component={Login}/> 
                    <Route exact path="/registration/" component={Registration} />
                    <Route exact path="/profile" component={Profile}/>
                    <Route exact path="/profile/association/animals" component={AnimalEditScreen}/>
                    <Route exact path="/profile/association/animals/add" component={AnimalAddScreen}/>
                    <Route path="/search" component={SearchScreen} />
                    <Route path="/profile/association/edit" component={AssociationEditScreen}/>
                    <Route path="/profile/citizen/edit" component={CitizenAccountEdit}/>
                    <Route path="/registration/citizen/" component={CitizenRegistration} />
                    <Route path="/registration/association/" component={AssociationRegistration} />
                    <Route path="/association/:id" component={AssociationScreen} />
                    <Route path="/statistics" component={Statistics} />
                    <Route path="/walk/confirm" component={WalkConfirmationScreen} />
                    <Route path="/walk/confirmed" component={WalkConfirmedScreen} />
                    <Route path="/calendar" component={CalendarScreen} />
                </Switch>
            </div>
        </Router>
    );
}

export default HomeRouter;
