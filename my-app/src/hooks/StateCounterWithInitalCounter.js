import React, { useReducer } from 'react';

function init(initialCount) {
  return { count: initialCount };
}

function reducer(state, action) {
  switch (action.type) {
    case 'increment':
      return { count: state.count + 1 };

    case 'decrement':
      return { count: state.count - 1 };

    case 'reset':
      return init(action.payload);

    default:
      return new Error();
  }
}

export function StateCounterWithInitialCounter({ initialCount }) {
  const [state, dispatch] = useReducer(reducer, initialCount, init);

  return (
    <>
      <div>Count:{state.count}</div>

      <button onClick={() => dispatch({ type: 'increment' })}>Increment</button>
      <button onClick={() => dispatch({ type: 'decrement' })}>Decrement</button>
      <button
        onClick={() => dispatch({ type: 'reset', payload: initialCount })}
      >
        Reset
      </button>
    </>
  );
}
