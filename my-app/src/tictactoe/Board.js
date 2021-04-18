import { Square } from './Square';
import React from 'react';

const style = {
  border: '4px solid darkblue',
  borderRadius: '10px',
  width: '250px',
  height: '250px',
  margin: '0 auto',
  display: 'grid',
  gridTemplate: 'repeat(3, 1fr) / repeat(3, 1fr)',
};

export function Board({ squares, onClick }) {
  return (
    <>
      <div className='board' style={style}>
        {squares.map((square, i) => (
          <Square key={i} value={square} onClick={() => onClick(i)} />
        ))}
      </div>
    </>
  );
}
