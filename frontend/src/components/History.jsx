import { useState, useEffect } from 'react';
import { qmaService } from '../services/api';
import { useAuth } from '../context/AuthContext';
import './History.css';

/**
 * History Component
 * Displays user's operation history
 */
export function History({ isOpen, onClose }) {
  const { isAuthenticated } = useAuth();
  const [history, setHistory] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  // Fetch history when modal opens
  useEffect(() => {
    if (isOpen && isAuthenticated) {
      fetchHistory();
    }
  }, [isOpen, isAuthenticated]);

  const fetchHistory = async () => {
    setLoading(true);
    setError('');

    try {
      const data = await qmaService.getHistory();
      setHistory(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  if (!isOpen) return null;

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="history-modal" onClick={(e) => e.stopPropagation()}>
        <div className="modal-header">
          <h2>Operation History</h2>
          <button className="close-btn" onClick={onClose}>
            &times;
          </button>
        </div>

        <div className="modal-body">
          {!isAuthenticated ? (
            <div className="empty-state">
              <p>Please login to view your history</p>
            </div>
          ) : loading ? (
            <div className="loading">Loading history...</div>
          ) : error ? (
            <div className="error-state">{error}</div>
          ) : history.length === 0 ? (
            <div className="empty-state">
              <p>No history yet. Start measuring!</p>
            </div>
          ) : (
            <div className="history-list">
              {history.map((item) => (
                <div key={item.id} className="history-item">
                  <div className="history-operation">{item.operation}</div>
                  <div className="history-details">
                    {formatHistoryItem(item)}
                  </div>
                  <div className="history-time">
                    {new Date(item.timestamp).toLocaleString()}
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

/**
 * Format history item for display
 */
function formatHistoryItem(item) {
  switch (item.operation) {
    case 'CONVERT':
      return `${item.firstValue} ${item.firstUnit} → ${item.result.toFixed(2)} ${item.resultUnit}`;
    case 'COMPARE':
      return `${item.firstValue} ${item.firstUnit} vs ${item.secondValue} ${item.secondUnit}`;
    default:
      return `${item.firstValue} ${item.firstUnit} ${getOperationSymbol(item.operation)} ${item.secondValue} ${item.secondUnit} = ${item.result.toFixed(2)} ${item.resultUnit}`;
  }
}

/**
 * Get symbol for operation
 */
function getOperationSymbol(operation) {
  switch (operation) {
    case 'ADD':
      return '+';
    case 'SUBTRACT':
      return '-';
    case 'MULTIPLY':
      return '×';
    case 'DIVIDE':
      return '÷';
    default:
      return operation;
  }
}
