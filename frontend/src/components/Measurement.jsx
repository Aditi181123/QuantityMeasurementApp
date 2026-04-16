import { useState } from 'react';
import { qmaService, UNITS, MEASUREMENT_TYPES } from '../services/api';
import { useAuth } from '../context/AuthContext';
import './Measurement.css';

/**
 * Measurement Component
 * All operations work WITHOUT login - only History requires login
 */
export function Measurement() {
  const { isAuthenticated } = useAuth();
  const [operation, setOperation] = useState('convert');
  const [measurementType, setMeasurementType] = useState('LENGTH');
  const [firstValue, setFirstValue] = useState('');
  const [firstUnit, setFirstUnit] = useState('FEET');
  const [secondValue, setSecondValue] = useState('');
  const [secondUnit, setSecondUnit] = useState('INCH');
  const [result, setResult] = useState(null);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  // Update units when measurement type changes
  const handleTypeChange = (type) => {
    setMeasurementType(type);
    setFirstUnit(UNITS[type][0]);
    setSecondUnit(UNITS[type][1] || UNITS[type][0]);
    setResult(null);
    setError('');
  };

  // Perform the selected operation - ALL WORK WITHOUT LOGIN
  const performOperation = async () => {
    setError('');
    setResult(null);

    // Validation
    if (!firstValue) {
      setError('Please enter the first value');
      return;
    }

    if (operation !== 'convert' && !secondValue) {
      setError('Please enter the second value');
      return;
    }

    if (operation === 'divide' && parseFloat(secondValue) === 0) {
      setError('Cannot divide by zero');
      return;
    }

    setLoading(true);

    try {
      let response;

      switch (operation) {
        case 'convert':
          response = await qmaService.convert(
            parseFloat(firstValue),
            firstUnit,
            secondUnit
          );
          setResult(`${firstValue} ${firstUnit} = ${response.result.toFixed(4)} ${response.unit}`);
          break;

        case 'compare':
          response = await qmaService.compare(
            parseFloat(firstValue),
            firstUnit,
            parseFloat(secondValue),
            secondUnit
          );
          setResult(`${firstValue} ${firstUnit} ${response.comparison} ${secondValue} ${secondUnit}`);
          break;

        case 'add':
          response = await qmaService.add(
            parseFloat(firstValue),
            firstUnit,
            parseFloat(secondValue),
            secondUnit
          );
          setResult(`${firstValue} ${firstUnit} + ${secondValue} ${secondUnit} = ${response.result.toFixed(2)} ${response.resultUnit}`);
          break;

        case 'subtract':
          response = await qmaService.subtract(
            parseFloat(firstValue),
            firstUnit,
            parseFloat(secondValue),
            secondUnit
          );
          setResult(`${firstValue} ${firstUnit} - ${secondValue} ${secondUnit} = ${response.result.toFixed(2)} ${response.resultUnit}`);
          break;

        case 'multiply':
          response = await qmaService.multiply(
            parseFloat(firstValue),
            firstUnit,
            parseFloat(secondValue),
            secondUnit
          );
          setResult(`${firstValue} ${firstUnit} × ${secondValue} ${secondUnit} = ${response.result.toFixed(2)} ${response.resultUnit}`);
          break;

        case 'divide':
          response = await qmaService.divide(
            parseFloat(firstValue),
            firstUnit,
            parseFloat(secondValue),
            secondUnit
          );
          setResult(`${firstValue} ${firstUnit} ÷ ${secondValue} ${secondUnit} = ${response.result.toFixed(2)} ${response.resultUnit}`);
          break;

        default:
          setError('Unknown operation');
      }
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const isConvert = operation === 'convert';
  const isTemperature = measurementType === 'TEMPERATURE';
  const temperatureBlocked = isTemperature && ['add', 'subtract', 'multiply', 'divide'].includes(operation);

  return (
    <div className="measurement-container">
      <h2>Quantity Measurement</h2>

      {/* Operation Selection */}
      <div className="operations-grid">
        {['convert', 'compare', 'add', 'subtract', 'multiply', 'divide'].map((op) => (
          <button
            key={op}
            className={`op-btn ${operation === op ? 'active' : ''}`}
            onClick={() => {
              setOperation(op);
              setResult(null);
              setError('');
            }}
          >
            {op.charAt(0).toUpperCase() + op.slice(1)}
          </button>
        ))}
      </div>

      {/* Measurement Type Selection */}
      <div className="type-selection">
        <label>Measurement Type:</label>
        <div className="type-buttons">
          {MEASUREMENT_TYPES.map((type) => (
            <button
              key={type}
              className={`type-btn ${measurementType === type ? 'active' : ''}`}
              onClick={() => handleTypeChange(type)}
            >
              {type}
            </button>
          ))}
        </div>
      </div>

      {/* Input Fields */}
      <div className="input-section">
        <div className="input-group">
          <label>{isConvert ? 'From Value' : 'First Value'}</label>
          <input
            type="number"
            value={firstValue}
            onChange={(e) => setFirstValue(e.target.value)}
            placeholder="Enter value"
          />
          <select value={firstUnit} onChange={(e) => setFirstUnit(e.target.value)}>
            {UNITS[measurementType].map((unit) => (
              <option key={unit} value={unit}>
                {unit}
              </option>
            ))}
          </select>
        </div>

        {isConvert ? (
          <div className="input-group">
            <label>To Unit</label>
            <select value={secondUnit} onChange={(e) => setSecondUnit(e.target.value)}>
              {UNITS[measurementType].map((unit) => (
                <option key={unit} value={unit}>
                  {unit}
                </option>
              ))}
            </select>
          </div>
        ) : (
          <div className="input-group">
            <label>Second Value</label>
            <input
              type="number"
              value={secondValue}
              onChange={(e) => setSecondValue(e.target.value)}
              placeholder="Enter value"
            />
            <select value={secondUnit} onChange={(e) => setSecondUnit(e.target.value)}>
              {UNITS[measurementType].map((unit) => (
                <option key={unit} value={unit}>
                  {unit}
                </option>
              ))}
            </select>
          </div>
        )}
      </div>

      {/* Temperature Warning for Arithmetic Operations */}
      {temperatureBlocked && (
        <div className="auth-warning">
          Temperature cannot be used in arithmetic operations
        </div>
      )}

      {/* Calculate Button - ALWAYS ENABLED (no login required) */}
      <button
        className="calculate-btn"
        onClick={performOperation}
        disabled={loading || temperatureBlocked}
      >
        {loading ? 'Calculating...' : 'Calculate'}
      </button>

      {/* Result Display */}
      {result && (
        <div className="result-box success">
          <h3>Result</h3>
          <p>{result}</p>
        </div>
      )}

      {/* Error Display */}
      {error && (
        <div className="result-box error">
          <h3>Error</h3>
          <p>{error}</p>
        </div>
      )}

      
    </div>
  );
}
