export default function ElectionLayout({ children }) {
  return (
    <div className="min-h-screen w-full bg-gradient-to-br from-blue-50 to-indigo-50 flex items-center justify-center p-8">
      {children}
    </div>
  );
}
