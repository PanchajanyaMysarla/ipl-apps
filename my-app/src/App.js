import React from 'react';
import { BrowserRouter as Router, Link, Route, Switch } from 'react-router-dom';
import { RootCounter } from './hooks/RootCounter';
import { About } from './learn-route/About';
import { Home } from './learn-route/Home';
import { Game } from './tictactoe/Game';

export default function App() {
  return (
    <Router>
      <div>
        <nav>
          <ul>
            <li>
              <Link to='/'>Home</Link>
            </li>
            <li>
              <Link to='/about'>About</Link>
            </li>
            <li>
              <Link to='/counter'>Counter</Link>
            </li>
            <li>
              <Link to='/game'>Game</Link>
            </li>
          </ul>
        </nav>

        {/* A <Switch> looks through its children <Route>s and
            renders the first one that matches the current URL. */}
        <Switch>
          <Route path='/about'>
            <About />
          </Route>
          <Route path='/counter'>
            <RootCounter />
          </Route>

          <Route path='/game'>
            <Game />
          </Route>

          <Route path='/'>
            <Home />
          </Route>
        </Switch>
      </div>
    </Router>
  );
}
