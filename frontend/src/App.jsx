import { useState } from 'react';
import { AuthProvider, useAuth } from './context/AuthContext';
import { AuthModal } from './components/Auth';
import { Measurement } from './components/Measurement';
import { History } from './components/History';
import './App.css';

/**
 * Main App Content
 * Contains navigation and renders main components
 */
function AppContent() {
  const { user, isAuthenticated, logout } = useAuth();
  const [showAuthModal, setShowAuthModal] = useState(false);
  const [showHistory, setShowHistory] = useState(false);

  return (
    <div className="app">
      {/* Header / Navigation */}
      <header className="header">
        <div className="logo">
          <h1>QMA</h1>
          <span>Quantity Measurement App</span>
        </div>

        <nav className="nav">
          {isAuthenticated ? (
            <>
              <span className="welcome">Welcome, {user.username}</span>
              <button onClick={() => setShowHistory(true)} className="nav-btn">
                History
              </button>
              <button onClick={logout} className="nav-btn logout">
                Logout
              </button>
            </>
          ) : (
            <button onClick={() => setShowAuthModal(true)} className="nav-btn login">
              Login / Sign Up
            </button>
          )}
        </nav>
      </header>

      {/* Main Content */}
      <main className="main">
        <Measurement />
      </main>

     
      {/* Modals */}
      <AuthModal
        isOpen={showAuthModal}
        onClose={() => setShowAuthModal(false)}
      />
      <History isOpen={showHistory} onClose={() => setShowHistory(false)} />
    </div>
  );
}

/**
 * App Component
 * Root component that wraps everything with AuthProvider
 */
function App() {
  return (
    <AuthProvider>
      <AppContent />
    </AuthProvider>
  );
}

export default App;
