// ProgressToast.js
import React from 'react';

function ProgressToast({ store, percent }) {
  return (
    <div style={{ minWidth: '200px' }}>
      <div>{store} is updating... {percent}%</div>
      <div style={{
        marginTop: '6px',
        height: '6px',
        backgroundColor: '#eee',
        borderRadius: '3px',
        overflow: 'hidden'
      }}>
        <div style={{
          width: `${percent}%`,
          height: '100%',
          backgroundColor: '#4caf50',
          transition: 'width 0.3s ease'
        }} />
      </div>
    </div>
  );
}

export default ProgressToast;
