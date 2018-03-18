'use strict';

import React from 'react';
import {render} from 'react-dom';

import {Provider} from 'react-redux';
import configureStore from './configureStore';

import {CookiesProvider} from 'react-cookie';

import App from './container/app';
import Profile from './container/app/Profile';

require('../common/styles/tbme-tv.css');

const store = configureStore();

render(
  <Provider store={store}>
    <CookiesProvider>
      <App>
        <Profile/>
      </App>
    </CookiesProvider>
  </Provider>
  , (document.getElementById('app'): any));