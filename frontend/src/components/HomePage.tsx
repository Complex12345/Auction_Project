import {AuctionItem} from "./AuctionItem.tsx";
import {AuctionList} from "../resources/AuctionItems.ts";

export function HomePage() {

    return(
        <div>
        <AuctionItem item={AuctionList[0]}/>
        </div>
    )
}