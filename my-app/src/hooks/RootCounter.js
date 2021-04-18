import React from 'react';
import { Counter } from './Counter';
import { StateCounter } from './StateCounter';
import { StateCounterWithInitialCounter } from './StateCounterWithInitalCounter';

export function RootCounter() {
  return (
    <>
      <Counter />
      <hr />
      <StateCounter />
      <hr />
      <StateCounterWithInitialCounter initialCount={5} />
    </>
  );
}
