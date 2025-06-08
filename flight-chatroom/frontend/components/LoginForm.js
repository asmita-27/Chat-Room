'use client';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';

export default function LoginForm() {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [theme, setTheme] = useState('light');
  const router = useRouter();

  useEffect(() => {
    const savedTheme = localStorage.getItem('theme') || 'light';
    setTheme(savedTheme);
    document.documentElement.classList.toggle('dark', savedTheme === 'dark');
  }, []);

  const toggleTheme = () => {
    const newTheme = theme === 'light' ? 'dark' : 'light';
    setTheme(newTheme);
    localStorage.setItem('theme', newTheme);
    document.documentElement.classList.toggle('dark', newTheme === 'dark');
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!username || !email) return;
    localStorage.setItem('username', username);
    localStorage.setItem('email', email);
    localStorage.setItem('room', 'default'); // fixed room
    router.push('/chat');
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-blue-50 dark:bg-gray-900 px-4 font-sans">
      <form
        onSubmit={handleSubmit}
        className="bg-blue-100 dark:bg-gray-800 text-black dark:text-white p-6 rounded shadow-lg w-full max-w-sm space-y-4"
      >
        <h1 className="text-xl font-bold text-center">Welcome to Flight Chat</h1>

        <input
          type="text"
          placeholder="Enter your name"
          className="w-full px-3 py-2 border rounded dark:bg-gray-700 dark:border-gray-600"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          type="email"
          placeholder="Enter your email"
          className="w-full px-3 py-2 border rounded dark:bg-gray-700 dark:border-gray-600"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <button
          type="submit"
          className="bg-blue-500 hover:bg-blue-600 text-white w-full py-2 rounded"
        >
          Enter Chat
        </button>

        {/* <button
          type="button"
          onClick={toggleTheme}
          className="text-sm text-center underline w-full mt-2"
        >
          Toggle {theme === 'light' ? 'Dark' : 'Light'} Mode
        </button> */}
      </form>
    </div>
  );
}
