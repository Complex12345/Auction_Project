import { Link } from 'react-router-dom';

export function MyAccountPage() {
    return (
        <div>
            <table>
                <tr>
                    <th><Link to="/accountSettings">Account Details</Link></th>
                </tr>

                <tr>
                    <th><Link to="/orderHistory">Order History</Link></th>
                </tr>

                <tr>
                    <th><Link to="/myBids">My Bids</Link></th>
                </tr>
            </table>
        </div>
    );
}