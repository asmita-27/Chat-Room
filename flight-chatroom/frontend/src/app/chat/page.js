'use client';
import { useEffect, useState } from 'react';
import socket from '../../socket';
import Message from '../../components/Message';

export default function ChatPage() {
  const [messages, setMessages] = useState([]);
  const [message, setMessage] = useState('');
  const [username, setUsername] = useState('');
  const [room, setRoom] = useState('');

  useEffect(() => {
    const u = localStorage.getItem('username');
    const r = localStorage.getItem('room');
    setUsername(u);
    setRoom(r);

    if (u && r) {
      socket.emit('join_room', { room: r, user: u });
    }

    socket.on('receive_message', (data) => {
      setMessages((prev) => [...prev, data]);
    });

    return () => {
      socket.emit('leave_room', r);
      socket.off('receive_message');
    };
  }, []);

  const sendMessage = () => {
    if (message.trim()) {
      socket.emit('send_message', {
        user: username,
        text: message,
        room,
      });
      setMessage('');
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col items-center justify-center px-4">
      <div className="w-full max-w-2xl bg-white rounded-lg shadow-lg p-4">
        <h1 className="text-2xl font-bold mb-4 text-center">Chatroom: {room}</h1>

        <div className="h-64 overflow-y-auto border p-4 rounded mb-4 bg-gray-100">
          {messages.map((msg, i) => (
            <Message key={i} user={msg.user} text={msg.text} />
          ))}
        </div>

        <div className="flex gap-2">
          <input
            className="flex-1 border rounded px-3 py-2"
            value={message}
            onChange={(e) => setMessage(e.target.value)}
            placeholder="Type a message..."
            onKeyDown={(e) => e.key === 'Enter' && sendMessage()}
          />
          <button
            onClick={sendMessage}
            className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded"
          >
            Send
          </button>
        </div>
      </div>
    </div>
  );
}
