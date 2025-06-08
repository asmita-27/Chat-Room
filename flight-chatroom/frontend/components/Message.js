export default function Message({ user, text }) {
    return (
        <div className="px-4 py-2 my-1 rounded-lg shadow w-fit max-w-md bg-blue-100">
            <span className="font-semibold">{user}:</span> {text}
        </div>
    );
}